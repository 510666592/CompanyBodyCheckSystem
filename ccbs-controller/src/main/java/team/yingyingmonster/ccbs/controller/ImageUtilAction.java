package team.yingyingmonster.ccbs.controller;






import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.yingyingmonster.ccbs.image.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@Controller
@RequestMapping("/Image")
public class ImageUtilAction {

    String imgCode = null;
    @RequestMapping("/createImg")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        createImg(request, response);
        return "success";
    }

    private void createImg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, BufferedImage> map = null;
        BufferedImage image = null;
        map = ImageUtil.createImage();

        imgCode = map.keySet().iterator().next();
        image = map.get(imgCode);
        req.getSession().setAttribute("imgCode", imgCode);
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }
    @RequestMapping("/checkcode")
    private void checkcode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code1= req.getParameter("kcode");
        PrintWriter out1=resp.getWriter();
        if(code1.equals(imgCode)){
            out1.println("true");
        }else{
            out1.println("false");
        }
        out1.flush();
        out1.close();
    }
}
