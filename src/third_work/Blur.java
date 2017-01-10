package third_work;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Created by Amanda on 17/1/6.
 * @description 高斯模糊
 */
public class Blur {
    //模糊半径设置
    private static final int RADIUS = 10;

    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("test.jpg"));
        System.out.println(image);

        int height = image.getHeight();
        int width = image.getWidth();

        //存储像素值的数组
        int[] pixels = new int[RADIUS * RADIUS];

        //遍历图片像素并处理
        for (int i = 0;i < width;i++) {
            for (int j = 0;j < height;j++) {
                dealPixel(image, i, j, pixels, width, height);
                image.setRGB(i,j,blurImage(pixels));
            }
        }

        //写入文件
        ImageIO.write(image,"jpeg",new File(System.currentTimeMillis()+".jpg"));
        System.out.println("------------图片处理完成-------------");
    }

    /**
     * 遍历图片像素，处理每个像素值的大小
     * @param image 图片
     * @param x     像素横坐标
     * @param y     像素纵坐标
     * @param pixels    存储像素值的数组
     * @param width     图片宽度
     * @param height    图片高度
     */
    private static void dealPixel(BufferedImage image,int x,int y,int[] pixels,int width,int height) {
        int point = 0;
        //处理像素值
        for (int i = x;i < RADIUS + x;i++) {
            for (int j = y;j < RADIUS + y;j++) {
                int tempX = i;
                if (i >= width) {
                    tempX = x;
                }
                int tempY = j;
                if (tempY >= height) {
                    tempY = y;
                }
                pixels[point++] = image.getRGB(tempX,tempY);
            }
        }
//        for (int i = 0;i < 9;i++) {
//            Color c = new Color(pixels[i]);
//            int r = c.getRed();
//            int g = c.getGreen();
//            int b = c.getBlue();
//            System.out.print(r + " " + g + "  " + b + "  ");
//        }
    }

    /**
     * 将处理过的像素填充进Color对象中
     * @param pixels
     * @return new Color(r,g,b).getRGB()
     */
    private static int blurImage(int[] pixels) {
        int r = 0,g = 0,b = 0;
        int k = 0;

        //将处理过的像素填充进新的Color对象中
        for (int j = 0; j < pixels.length; j++) {
            pixels[j] = pixels[k];
            k++;
            Color c = new Color(pixels[j]);
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
            //System.out.println(r+" "+g+" "+b);

        }

        //取平均值
        return new Color(r / RADIUS / RADIUS,g / RADIUS / RADIUS,b / RADIUS /RADIUS).getRGB();
    }
}
