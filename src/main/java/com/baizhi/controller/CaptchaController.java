package com.baizhi.controller;


import com.baizhi.utils.SecurityCode;
import com.baizhi.utils.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/captcha")
@SessionAttributes({"securityCode"})
public class CaptchaController {

    @RequestMapping("/creatCaptcha")//绘制验证码
    public void creatCaptcha(Model model, HttpServletResponse response) throws IOException {
        //获取验证码随机数
        String securityCode = SecurityCode.getSecurityCode();
        //将随机数保存在session中用来做验证
        model.addAttribute("securityCode",securityCode);
        //使用随机数绘制验证码
        BufferedImage image = SecurityImage.createImage(securityCode);
        //使用io响应验证码图片
        OutputStream out = response.getOutputStream();
        ImageIO.write(image,"png",out);

    }
}
