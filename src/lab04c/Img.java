package lab04c;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Img {
    public static void createImg(int size, int devider, int[] arr) {
        String path = "output/board.png";
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int rgb;
                if (arr[size * y + x] == 0) {
                    rgb = 255;
                    rgb = (rgb << 8);
                    rgb = (rgb << 8);
                } else {
                    rgb = 255 * arr[size * y + x] / devider;
                    rgb = (rgb << 8) + 255 * arr[size * y + x] / devider;
                    rgb = (rgb << 8) + 255 * arr[size * x + y] / devider;
                }
                image.setRGB(x, y, rgb);
            }
        }

        File ImageFile = new File(path);
        try {
            ImageIO.write(image, "png", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
