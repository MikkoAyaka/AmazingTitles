package sk.m3ii0.amazingtitles.basicpack;

import net.md_5.bungee.api.ChatColor;
import sk.m3ii0.amazingtitles.api.AmazingTitlesAPI;
import sk.m3ii0.amazingtitles.code.colors.ColorTranslator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BasicPack {
	
	public static void loadDefaultAnimations() {
		
		AmazingTitlesAPI.getApi().createAndRegister("NONE", false, true, true, (type, input, args) -> new ArrayList<>(Collections.singleton(ColorTranslator.colorize(input))));
		
		AmazingTitlesAPI.getApi().createAndRegister("FLASHING_SYMBOL_WRAP", true, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			String symbol = " " + args[0] + "&r ";
			frames.add(input);
			frames.add(ColorTranslator.colorize(symbol + input + symbol));
			return frames;
		}, "<Symbol(Just one word/character)>");
		
		AmazingTitlesAPI.getApi().createAndRegister("RAINBOW", true, true, false, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			String red = "#FF2424";
			String blue = "#002AFF";
			String green = "#00FF08";
			String smoothed = "          " + input + "          ";
			int length = smoothed.length();
			int withGradient = input.length()*17;
			int start = 10*17;
			/*
			 * Red (Blue ->) Green
			 * */
			for (int i = 0; i <= length; i++) {
				String in = smoothed.substring(0, i);
				String out = smoothed.substring(i);
				String var = "<" + red + ">&l" + in + "</" + blue + "><" + blue + ">&l" + out + "</" + green + ">";
				frames.add(ColorTranslator.colorize(var).substring(start).substring(0, withGradient));
			}
			/*
			 * Green (Red ->) Blue
			 * */
			for (int i = 0; i <= length; i++) {
				String in = smoothed.substring(0, i);
				String out = smoothed.substring(i);
				String var = "<" + green + ">&l" + in + "</" + red + "><" + red + ">&l" + out + "</" + blue + ">";
				frames.add(ColorTranslator.colorize(var).substring(start).substring(0, withGradient));
			}
			/*
			 * Blue (Green ->) Red
			 * */
			for (int i = 0; i <= length; i++) {
				String in = smoothed.substring(0, i);
				String out = smoothed.substring(i);
				String var = "<" + blue + ">&l" + in + "</" + green + "><" + green + ">&l" + out + "</" + red + ">";
				frames.add(ColorTranslator.colorize(var).substring(start).substring(0, withGradient));
			}
			return frames;
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("WAVES", true, true, false, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			String color1 = (String) args[0];
			String color2 = (String) args[1];
			String smoothed = "          " + input + "          ";
			int length = smoothed.length();
			int withGradient = input.length()*17;
			int start = 10*17;
			/*
			 * Red (Blue ->) Green
			 * */
			for (int i = 0; i <= length; i++) {
				String in = smoothed.substring(0, i);
				String out = smoothed.substring(i);
				String var = "<" + color1 + ">&l" + in + "</" + color2 + "><" + color2 + ">&l" + out + "</" + color1 + ">";
				frames.add(ColorTranslator.colorize(var).substring(start).substring(0, withGradient));
			}
			/*
			 * Green (Red ->) Blue
			 * */
			for (int i = 0; i <= length; i++) {
				String in = smoothed.substring(0, i);
				String out = smoothed.substring(i);
				String var = "<" + color2 + ">&l" + in + "</" + color1 + "><" + color1 + ">&l" + out + "</" + color2 + ">";
				frames.add(ColorTranslator.colorize(var).substring(start).substring(0, withGradient));
			}
			/*
			 * Blue (Green ->) Red
			 * */
			for (int i = 0; i <= length; i++) {
				String in = smoothed.substring(0, i);
				String out = smoothed.substring(i);
				String var = "<" + color1 + ">&l" + in + "</" + color2 + "><" + color2 + ">&l" + out + "</" + color1 + ">";
				frames.add(ColorTranslator.colorize(var).substring(start).substring(0, withGradient));
			}
			return frames;
		}, "<Color1(Hex/Legacy)>", "<Color2(Hex/Legacy)>");
		
		AmazingTitlesAPI.getApi().createAndRegister("BOUNCE", true, true, false, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			String smoothed = "          " + input + "          ";
			String color1 = (String) args[0];
			String color2 = (String) args[1];
			int length = smoothed.length();
			int withGradient = input.length()*17;
			int start = 10*17;
			for (int i = 0; i <= length; i++) {
				String to = smoothed.substring(0, i);
				String from = smoothed.substring(i);
				if (to.length() == 1 || from.length() == 1) continue;
				String formatted = "<" + color1 + ">&l" + to + "</" + color2 + ">" + "<" + color2 + ">&l" + from + "</" + color1 + ">";
				frames.add(ColorTranslator.colorize(formatted).substring(start).substring(0, withGradient));
			}
			int revertSize = frames.size();
			for (int i = revertSize-1; i > -1; i--) {
				String reversed = frames.get(i);
				frames.add(reversed);
			}
			return frames;
		}, "<Color1(Hex/Legacy)>", "<Color2(Hex/Legacy)>");
		
		AmazingTitlesAPI.getApi().createAndRegister("FLASHING", true, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			frames.add(ColorTranslator.colorize(input));
			frames.add("");
			return frames;
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("SPLIT", true, true, true, (type, input, args) -> {
			input = ColorTranslator.colorize(input);
			return new ArrayList<>(Arrays.asList(input.split("%frame%")));
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("WORDS_SPLIT", true, true, true, (type, input, args) -> {
			input = ColorTranslator.colorize(input);
			return new ArrayList<>(Arrays.asList(input.split(" ")));
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("FROM_LEFT", false, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			int lastSpaces = 180;
			StringBuilder spaces = new StringBuilder();
			for (int i = 0; i < lastSpaces; i++) {
				spaces.append(" ");
			}
			for (int i = 30; i > -1; i--) {
				int newSpaces = lastSpaces-(i*5);
				String formattedSpaces = spaces.substring(newSpaces);
				frames.add(ColorTranslator.colorize(input + formattedSpaces));
			}
			frames.add(ColorTranslator.colorize(input));
			return frames;
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("FROM_RIGHT", false, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			int lastSpaces = 180;
			StringBuilder spaces = new StringBuilder();
			for (int i = 0; i < lastSpaces; i++) {
				spaces.append(" ");
			}
			for (int i = 30; i > -1; i--) {
				int newSpaces = lastSpaces-(i*5);
				String formattedSpaces = spaces.substring(newSpaces);
				frames.add(ColorTranslator.colorize(formattedSpaces + input));
			}
			frames.add(ColorTranslator.colorize(input));
			return frames;
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("FADE_IN", false, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			List<String> chars = ColorTranslator.charactersWithColors(ColorTranslator.colorize(input));
			StringBuilder frameBuilder = new StringBuilder();
			for (String var : chars) {
				frameBuilder.append(var);
				frames.add(frameBuilder.toString());
			}
			return frames;
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("FADE_IN_WRITER", false, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			String symbol = ColorTranslator.colorize((String) args[0]);
			String colorizedInput = ColorTranslator.colorize(input);
			List<String> chars = ColorTranslator.charactersWithColors(colorizedInput);
			StringBuilder frameBuilder = new StringBuilder();
			frames.add(symbol);
			for (String var : chars) {
				frameBuilder.append(var);
				frames.add(frameBuilder.toString() + symbol);
			}
			frames.add(colorizedInput);
			return frames;
		}, "<Symbol(Writer)>");
		
		AmazingTitlesAPI.getApi().createAndRegister("FROM_BOTH_SIDES", false, true, true, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			int lastSpaces = 180;
			StringBuilder spaces = new StringBuilder();
			for (int i = 0; i < lastSpaces; i++) {
				spaces.append(" ");
			}
			String pre = input.substring(0, input.length()/2);
			String aft = input.substring(input.length()/2);
			for (int i = 30; i > -1; i--) {
				int newSpaces = lastSpaces-(i*6);
				String formattedSpaces = spaces.substring(newSpaces);
				String format = pre + formattedSpaces + aft;
				frames.add(ColorTranslator.colorize(format));
			}
			return frames;
		});
		
		AmazingTitlesAPI.getApi().createAndRegister("PULSING", true, true, false, (type, input, args) -> {
			List<String> frames = new ArrayList<>();
			String color1 = (String) args[0];
			String color2 = (String) args[1];
			Color from;
			Color to;
			if (color1.startsWith("#")) {
				from = Color.decode(color1);
			} else if (color1.startsWith("&")) {
				from = ColorTranslator.fromChatColor(ChatColor.getByChar(color1.charAt(1)));
			} else from = Color.WHITE;
			if (color2.startsWith("#")) {
				to = Color.decode(color2);
			} else if (color2.startsWith("&")) {
				to = ColorTranslator.fromChatColor(ChatColor.getByChar(color2.charAt(1)));
			} else to = Color.BLACK;
			int r_max = Math.max(from.getRed(), to.getRed());
			int g_max = Math.max(from.getGreen(), to.getGreen());
			int b_max = Math.max(from.getBlue(), to.getBlue());
			int r_min = Math.min(from.getRed(), to.getRed());
			int g_min = Math.min(from.getGreen(), to.getGreen());
			int b_min = Math.min(from.getBlue(), to.getBlue());
			int r_total = r_max - r_min;
			int g_total = g_max - g_min;
			int b_total = b_max - b_min;
			int total_max = 0;
			if (r_total > total_max) total_max = r_total;
			if (g_total > total_max) total_max = g_total;
			if (b_total > total_max) total_max = b_total;
			int lastMinR = r_min;
			int lastMinG = g_min;
			int lastMinB = b_min;
			int lastMaxR = r_max;
			int lastMaxG = g_max;
			int lastMaxB = b_max;
			for (int i = 0; i <= total_max; i++) {
				int r = lastMinR;
				int g = lastMinG;
				int b = lastMinB;
				if (lastMinR < r_max) {
					r = (lastMinR = lastMinR+5);
				}
				if (lastMinG < g_max) {
					g = (lastMinG = lastMinG+5);
				}
				if (lastMinB < b_max) {
					b = (lastMinB = lastMinB+5);
				}
				if (r > r_max) r = r_max;
				if (g > g_max) g = g_max;
				if (b > b_max) b = b_max;
				Color c = new Color(r, g, b);
				String format = ColorTranslator.fromColor(c) + input;
				frames.add(ColorTranslator.colorize(format));
				if (r == r_max && g == g_max & b == b_max) {break;}
			}
			for (int i = 0; i <= total_max-1; i++) {
				int r = lastMaxR;
				int g = lastMaxG;
				int b = lastMaxB;
				if (lastMaxR > r_min) {
					r = (lastMaxR = lastMaxR-5);
				}
				if (lastMaxG > g_min) {
					g = (lastMaxG = lastMaxG-5);
				}
				if (lastMaxB > b_min) {
					b = (lastMaxB = lastMaxB-5);
				}
				if (r < r_min) r = r_min;
				if (g < g_min) g = g_min;
				if (b < b_min) b = b_min;
				Color c = new Color(r, g, b);
				String format = ColorTranslator.fromColor(c) + input;
				frames.add(ColorTranslator.colorize(format));
				if (r == r_min && g == g_min & b == b_min) {break;}
			}
			return frames;
		}, "<Color1(Hex/Legacy)>", "<Color2(Hex/Legacy)>");
		
	}
	
}
