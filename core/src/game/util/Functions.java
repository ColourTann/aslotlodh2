package game.util;

import java.util.List;

public class Functions {
	public static void shuffle(List input){
		for(int i=0;i<input.size();i++){
			Object o = input.remove(0);
			input.add((int) (Math.random()*input.size()), o);
		}
	}
}
