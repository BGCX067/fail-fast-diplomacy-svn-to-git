package Utilities;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.*;

	public class Hex {
		public static LinkedList<Hex> hexList=new LinkedList<Hex>();
		static int num =0;
		static boolean painted=false;
		Polygon hex;
		int thisNum;
		int size = 10;
		Color color=Color.gray;

		public Hex(int x, int y, int s) {
			hexList.add(this);
			size = s;
			thisNum=num;
			num++;
			int[] xA = new int[6];
			int[] yA = new int[6];
			xA[0] = x;
			yA[0] = y;
			xA[1] = x + size;
			yA[1] = y;
			xA[2] = x + size + size / 2;
			yA[2] = (int) (size * (Math.sqrt(3) / 2)) + y;
			xA[3] = x + size;
			yA[3] = (int) (2 * size * (Math.sqrt(3) / 2)) + y;
			xA[4] = x;
			yA[4] = (int) (2 * size * (Math.sqrt(3) / 2)) + y;
			xA[5] = x - (size / 2);
			yA[5] = (int) (size * (Math.sqrt(3) / 2)) + y;
			hex = new Polygon(xA, yA, 6);

		}
		public Polygon getHex(){
			return hex;
		}
		public void setColor(Color c){
			color=c;
		}
		public String toString(){
			return ""+size+" "+thisNum;
		}
		public Color getColor(){
			return color;
		}

		public Polygon getPoly() {
			return hex;
		}
		public static Hex getHex(Point p){
			Hex returner = null;
			for (int i=0;i<hexList.size();i++){
				if (hexList.get(i).getHex().contains(p)){
					returner=hexList.get(i);
				}
			}
			return returner;
		}
	}