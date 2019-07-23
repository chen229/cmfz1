package com.baizhi.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override//登录
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public String findByUsernameAndPwd(String username,
                                       String password,
                                       String enCode,
                                       HttpSession session) {
        String message;
        String securityCode = (String) session.getAttribute("securityCode");
        Admin admin=new Admin();
        admin.setUsername(username);
        Admin admin1 = adminDao.selectOne(admin);
        if (securityCode.equalsIgnoreCase(enCode)){
            if (admin1==null){
                message="该用户不存在";
            }else {
                if (admin1.getPassword().equals(password)){
                    message="登录成功";
                    session.setAttribute("admin",admin1);
                }else {
                    message="密码错误";
                }
            }
        } else {
            message="验证码错误";
        }
        return message;
    }

    @Override//用户注册
    public void insertAdmin(Admin admin) {
        String uuid = UUID.randomUUID().toString();
        admin.setId(uuid);
        int i = adminDao.insertSelective(admin);

        if (i==0){
            throw new RuntimeException("用户注册失败");
        }
    }

    @Override//根据用户名查找用户
    public Admin findByUsername(String username) {
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin admin1 = adminDao.selectOne(admin);
        return admin1;
    }

    @Override
    public void sendMessage(HttpSession session) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
//替换成你的AK
        final String accessKeyId = "LTAITCRe25R4wSK8";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "XqwaL6gXyEAperUQTynlQNs4rWEgax";//你的accessKeySecret，参考本文档步骤2
//初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers("13183376510");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("贝茗怡清欢");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_171117124");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        int random = (int) ((Math.random()*9+1)*100000);//生成随机验证码
        String code = String.valueOf(random);
        request.setTemplateParam("{'code':'"+code+"'}");
        session.setAttribute("code",code);
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
//请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println(sendSmsResponse.getCode());//打印这个信息,可以帮助我们及时调错
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//请求成功
        }
    }
}
