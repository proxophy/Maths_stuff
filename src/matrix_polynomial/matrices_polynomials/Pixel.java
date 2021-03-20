//Kann ignoriert werden
package matrices_polynomials;

import java.util.ArrayList;
import java.util.Collections;

public class Pixel implements Comparable<Pixel> {
	int brightness;
	int[] colors;
	float posx;
	float posy;
	String name;
	
	public static void main(String[] args){
		int[] color1 = new int[] {100, 200, 100};
		int[] color2 = new int[] {100, 200, 250};
		Pixel pixel1 = new Pixel(80, color1, 100, 100, "pixel1");
		Pixel pixel2 = new Pixel(60, color2, 100, 100, "pixel2");
		
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		pixels.add(pixel1);
		pixels.add(pixel2);
		
		System.out.println(pixels);
		Collections.sort(pixels);
		System.out.println(pixels);
		
	}
	
	public Pixel(int brightness, int[] colors, float posx, float posy, String name) {
		this.brightness = brightness;
		this.colors = colors;
		this.posx = posx;
		this.posy = posy;
		this.name = name;
	}

	@Override
	public int compareTo(Pixel other) {
		if (this.brightness < other.brightness) {
			return -1;
		} else if (this.brightness == other.brightness) {
			return 0;
		} else {
			return 1;
		}
		
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
