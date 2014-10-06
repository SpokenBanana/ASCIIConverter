import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class ASCIIPic {
    String intense = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,\"`\'.";

    public static void main(String[] args) {
        ASCIIPic p = new ASCIIPic();
        String line = "start";
        Scanner s = new Scanner(System.in);

        System.out.println("Please enter in a file path to a picture or enter B to browse for a picture! [Enter nothing to quit]: ");
        while (!(line = s.nextLine()).equals("")) {

            BufferedImage bi = null;
            if (line.equalsIgnoreCase("b")) {
                try{
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileFilter(new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png"));
                    JFrame frame = new JFrame();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    int returnVal = chooser.showOpenDialog(frame);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        String path = chooser.getSelectedFile().getPath();
                        frame.dispose();
                        bi = ImageIO.read(new File(path));
                    }
                } catch (Exception ex) {
                    System.out.println("Please choose a valid image file");
                    continue;
                }
            }
            else {
                try{
                    bi = ImageIO.read(new File(line));
                } catch (Exception e) {
                    System.out.printf("The command \"%s\" is not recognized or not a real file path, try again.\n\n", line);
                    continue;
                }
            }
            System.out.print("Please enter in an integer value to specify width you want the picture to be in (Enter 0 to print in original width): ");
            int width = -1;
            while ( (width = s.nextInt()) < 0)
                System.out.print("The number cannot be a negative, try again: ");
            s.nextLine();
            p.draw(bi, (width == 0) ? bi.getWidth() : width);
            System.out.println("Please enter in a file path to a picture or enter B to browse for a picture! [Enter nothing to quit]: ");
        }
        System.out.println("Thank you!");
    }
    public void draw(BufferedImage pic, int width) {
        double ratio = pic.getHeight() / (double) pic.getWidth();
        char[][] pix = new char[(int)(width * ratio)][width];
        if (pic.getWidth() < width) {
            pix = new char[pic.getHeight()][pic.getWidth()];
        }

        int boxh = (int) Math.round(pic.getHeight() / (double)pix.length);
        int boxw = (int) Math.round(pic.getWidth() / (double)pix[0].length);

        for (int i = 0; i < pix.length; i++) {
            for (int j = 0; j < pix[0].length; j++) {
                int sum = 0, cols, rows = 0;
                for (cols = i * boxh; cols < (i *boxh) + boxh && cols < pic.getHeight(); cols++) {
                    for (rows = j * boxw; rows < (j * boxw) + boxw && rows < pic.getWidth(); rows++) {
                        int r, g, b;
                        Color c  = new Color(pic.getRGB(rows, cols));
                        r = c.getRed();
                        g = c.getGreen();
                        b = c.getBlue();
                        sum += (int) ((r + g + b) / 3);
                    }
                }
                sum /= (boxh * boxw);
                pix[i][j] = intense.charAt((int)(sum / 3.9));
            }
        }

        for (char[] i : pix)
        {
            for (char j : i)
                System.out.print(j);
            System.out.println("");
        }
    }
}
