import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 

public class KMeans {
    public static void main(String [] args){
	if (args.length < 3){
	    System.out.println("Usage: Kmeans <input-image> <k> <output-image>");
	    return;
	}
	try{
	    BufferedImage originalImage = ImageIO.read(new File(args[0]));
	    int k=Integer.parseInt(args[1]);
	    BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
	    ImageIO.write(kmeansJpg, "jpg", new File(args[2])); 
	    
	}catch(IOException e){
	    System.out.println(e.getMessage());
	}	
    }
    
    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k){
	int w=originalImage.getWidth();
	int h=originalImage.getHeight();
	BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
	Graphics2D g = kmeansImage.createGraphics();
	g.drawImage(originalImage, 0, 0, w,h , null);
	// Read rgb values from the image
	int[] rgb=new int[w*h];
	int count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		rgb[count++]=kmeansImage.getRGB(i,j);
	    }
	}
	// Call kmeans algorithm: update the rgb values
	kmeans(rgb,k);

	// Write the new rgb values to the image
	count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		kmeansImage.setRGB(i,j,rgb[count++]);
	    }
	}
	return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private static void kmeans(int[] rgb, int k){
      int len = rgb.length;
      double[][] data = new double[len][3];
      for (int i=0; i<len; i++){
    	  Color mycolor = new Color(rgb[i]);
    	  int blue = mycolor.getBlue();
    	  int green = mycolor.getGreen();
      	  int red = mycolor.getRed();
    	  data[i][0] = red;
    	  data[i][1] = green;
    	  data[i][2] = blue;
          }
      double[][] centroids = new double[k][3];
      double threshold = 0.001;
      for (int i=0; i<k; i++){
    	  int c = (int) (Math.random()*len);
    	  for (int j=0; j<3;j++) {
    	  centroids[i][j] = data[c][j];
    	  }
      }
      double[][] c1 = centroids;
      int[] label = new int[len];
      while (true){
        centroids = c1;

        for (int i=0; i<len; i++){
          label[i] = cluster(data[i],centroids,k);
        }
        
        c1 = updateCentroids(label, data,k);
        if (converge(centroids, c1, threshold,k))
          break;
      }
      
      for (int i=0; i<len; i++){
          int a = label[i];
          rgb[i] = new Color((int)centroids[a][0], (int)centroids[a][1], (int)centroids[a][2]).getRGB();
      } 
	}
	
	private static int cluster(double[] v, double[][] cent,int k){
    double mindist = distance(v, cent[0]);
    int label =0;
    for (int i=1; i<k; i++){
      double t = distance(v, cent[i]);
      if (mindist>t){
        mindist = t;
        label = i;
      }
    }
    return label;
  }
  
  private static double distance(double[] v1, double[] v2){
	  double sum = 0;
	  for (int i=0; i<3; i++){
	      double d = v1[i]-v2[i];
	      sum += d*d;
	    }
	    return Math.sqrt(sum);
  }
  
  private static double[][] updateCentroids(int[] label, double[][] data,int k){
    double[][] newcent = new double[k][3];
    int[] counts = new int[k];

    for (int i=0; i<k; i++){
      counts[i] =0;
      for (int j=0; j<3; j++)
        newcent[i][j] =0;
    }
    int len = label.length;
    for (int i=0; i<len; i++){
      int cn = label[i];
      for (int j=0; j<3; j++){
          newcent[cn][j] += data[i][j];
    }
      counts[cn]++;
    }
    for (int z=0; z<k; z++){
    	for (int j=0; j<3; j++){
            newcent[z][j]/= counts[z];
          }
    } 
    return newcent;
  }
  
  private static boolean converge(double[][] c1, double[][] c2, double threshold,int k){
    double maxv = 0;
    for (int i=0; i<k; i++){
        double d= distance(c1[i], c2[i]);
        if (maxv<d)
            maxv = d;
    } 

    if (maxv <threshold)
      return true;
    else
      return false;
    
  }

}