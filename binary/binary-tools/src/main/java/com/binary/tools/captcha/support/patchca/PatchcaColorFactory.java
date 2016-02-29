package com.binary.tools.captcha.support.patchca;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.patchca.color.ColorFactory;

import com.binary.core.util.BinaryUtils;
import com.binary.tools.exception.CaptchaException;



/**
 * 外部自定义ColorFactory
 * 按常用颜色随机生成
 * @author wanwb
 */
public class PatchcaColorFactory implements ColorFactory, Serializable {
	private static final long serialVersionUID = 7888907242759758422L;
	
	
	private List<Color> colors;
	
	
	public PatchcaColorFactory() {
		colors = new ArrayList<Color>();
		colors.add(new Color(220,20,60));        //Crimson 猩红 (深红) #DC143C 220,20,60        
		colors.add(new Color(255,20,147));       //DeepPink 深粉红 #FF1493 255,20,147            
		colors.add(new Color(199,21,133));       //MediumVioletRed 中紫罗兰红 #C71585 199,21,133 
		colors.add(new Color(255,0,255));        //Magenta 洋红 (品红 玫瑰红) #FF00FF 255,0,255  
		colors.add(new Color(128,0,128));        //Purple 紫色 #800080 128,0,128                 
		colors.add(new Color(148,0,211));        //DarkViolet 暗紫罗兰 #9400D3 148,0,211         
		colors.add(new Color(75,0,130));         //Indigo 靛青 (紫兰色) #4B0082 75,0,130         
		colors.add(new Color(138,43,226));       //BlueViolet 蓝紫罗兰 #8A2BE2 138,43,226        
		colors.add(new Color(72,61,139));        //DarkSlateBlue 暗板岩蓝 #483D8B 72,61,139      
		colors.add(new Color(0,0,255));          //Blue 纯蓝 #0000FF 0,0,255                     
		colors.add(new Color(0,0,205));          //MediumBlue 中蓝色 #0000CD 0,0,205             
		colors.add(new Color(25,25,112));        //MidnightBlue 午夜蓝 #191970 25,25,112         
		colors.add(new Color(0,0,139));          //DarkBlue 暗蓝色 #00008B 0,0,139               
		colors.add(new Color(0,0,128));          //Navy 海军蓝 #000080 0,0,128                   
		colors.add(new Color(95,158,160));       //CadetBlue 军服蓝 #5F9EA0 95,158,160           
		colors.add(new Color(0,255,255));        //Cyan 青色 #00FFFF 0,255,255                   
		colors.add(new Color(0,206,209));        //DarkTurquoise 暗绿宝石 #00CED1 0,206,209      
		colors.add(new Color(47,79,79));         //DarkSlateGray 暗石板灰 #2F4F4F 47,79,79       
		colors.add(new Color(0,128,128));        //Teal 水鸭色 #008080 0,128,128                 
		colors.add(new Color(32,178,170));       //LightSeaGreen 浅海洋绿 #20B2AA 32,178,170     
		colors.add(new Color(64,224,208));       //Turquoise 绿宝石 #40E0D0 64,224,208           
		colors.add(new Color(0,255,127));        //SpringGreen 春绿色 #00FF7F 0,255,127          
		colors.add(new Color(60,179,113));       //MediumSeaGreen 中海洋绿 #3CB371 60,179,113    
		colors.add(new Color(46,139,87));        //SeaGreen 海洋绿 #2E8B57 46,139,87             
		colors.add(new Color(50,205,50));        //LimeGreen 闪光深绿 #32CD32 50,205,50          
		colors.add(new Color(34,139,34));        //ForestGreen 森林绿 #228B22 34,139,34          
		colors.add(new Color(0,128,0));          //Green 纯绿 #008000 0,128,0                    
		colors.add(new Color(0,100,0));          //DarkGreen 暗绿色 #006400 0,100,0              
		colors.add(new Color(85,107,47));        //DarkOliveGreen 暗橄榄绿 #556B2F 85,107,47     
		colors.add(new Color(154,205,50));       //YellowGreen 黄绿色 #9ACD32 154,205,50         
		colors.add(new Color(107,142,35));       //OliveDrab 橄榄褐色 #6B8E23 107,142,35         
		colors.add(new Color(128,128,0));        //Olive 橄榄 #808000 128,128,0                  
		colors.add(new Color(255,215,0));        //Gold 金色 #FFD700 255,215,0                   
		colors.add(new Color(218,165,32));       //Goldenrod 金菊黄 #DAA520 218,165,32           
		colors.add(new Color(184,134,11));       //DarkGoldenrod 暗金菊黄 #B8860B 184,134,11     
		colors.add(new Color(255,165,0));        //Orange 橙色 #FFA500 255,165,0                 
		colors.add(new Color(255,140,0));        //DarkOrange 深橙色 #FF8C00 255,140,0           
		colors.add(new Color(205,133,63));       //Peru 秘鲁 #CD853F 205,133,63                  
		colors.add(new Color(210,105,30));       //Chocolate 巧克力 #D2691E 210,105,30           
		colors.add(new Color(139,69,19));        //SaddleBrown 马鞍棕色 #8B4513 139,69,19        
		colors.add(new Color(255,127,80));       //Coral 珊瑚 #FF7F50 255,127,80                 
		colors.add(new Color(255,99,71));        //Tomato 番茄红 #FF6347 255,99,71               
		colors.add(new Color(250,128,114));      //Salmon 鲜肉(鲑鱼)色 #FA8072 250,128,114       
		colors.add(new Color(255,0,0));          //Red 纯红 #FF0000 255,0,0                      
		colors.add(new Color(165,42,42));        //Brown 棕色 #A52A2A 165,42,42                  
		colors.add(new Color(178,34,34));        //FireBrick 耐火砖 #B22222 178,34,34            
		colors.add(new Color(139,0,0));          //DarkRed 深红色 #8B0000 139,0,0                
		colors.add(new Color(128,128,128));      //Gray 灰色 #808080 128,128,128                 
		colors.add(new Color(0,0,0));            //Black 纯黑 #000000 0,0,0                                              
	}
	
	
	
	public PatchcaColorFactory(List<Color> colors) {
		this.setColors(colors);
	}
	
	public PatchcaColorFactory(String colors) {
		this.setColors(colors);
	}
	
	
	
	
	

	public List<Color> getColors() {
		return colors;
	}



	public void setColors(List<Color> colors) {
		if(colors==null || colors.size()==0) throw new CaptchaException(" the colors is empty! ");
		this.colors = colors;
	}
	
	
	/**
	 * 字符串形式Color, 格式为：255,255,233	多个Color以|分隔
	 * @param colors
	 */
	public void setColors(String colors) {
		if(colors==null || (colors=colors.trim()).length()==0) throw new CaptchaException(" the colors is empty! ");
		
		List<Color> list = new ArrayList<Color>();
		String[] arr = colors.split("[|]");
		for(int i=0; i<arr.length; i++) {
			String cs = arr[i];
			try {
				int s1 = cs.indexOf(',');
				int s2 = cs.lastIndexOf(',');
				int r = Integer.parseInt(cs.substring(0, s1));
				int g = Integer.parseInt(cs.substring(s1+1, s2));
				int b = Integer.parseInt(cs.substring(s2+1, cs.length()));
				list.add(new Color(r, g, b));
			}catch(Exception e) {
				throw new CaptchaException(" is wrong color string '"+arr[i]+"'! ", e);
			}
		}
	}


	

	@Override
	public Color getColor(int index) {
		int i = (int)BinaryUtils.random(this.colors.size());
		return colors.get(i);
	}
	
	
	
	
	
	
	
	
	
	

}



