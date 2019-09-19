<%@ page contentType="image/jpeg"
	import="java.awt.*,java.awt.image.BufferedImage,java.util.Random,com.sun.image.codec.jpeg.JPEGCodec"%>
<jsp:directive.page import="com.sun.image.codec.jpeg.JPEGImageEncoder"/>
<%
	response.addHeader("P3P","CP=CAO PSA OUR");
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	long begin=System.currentTimeMillis();
	//set backgroud color
	Color backColor = new Color(10,10,10);

	//set font color
	Color fontColor = new Color(250,250,250);

	int maxlength = 0;
	int width = 0;
	int height = 20;
	if (request.getParameter("width") != null) {
		maxlength = Integer.parseInt(request.getParameter("width"));
	} else {
		maxlength = 4;
	}
	width = maxlength * 15;
	BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);


	Graphics g = image.getGraphics();
	Random random = new Random();
	g.setColor(backColor);
	g.fillRect(0, 0, width, height);
	g.setFont(new Font("宋体", Font.PLAIN, 18));
	g.setColor(backColor);
	for (int i = 0; i < 0; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x, y, x + xl, y + yl);
	}

	String sRand = "";
	for (int i = 0; i < maxlength; i++) {
		String rand = String.valueOf(random.nextInt(10));
		sRand += rand;
		g.setColor(fontColor);
		g.drawString(rand, 13*i+6 , 16);
	}
	session.setAttribute("validateCode", sRand);
	g.dispose();
	out.clear();
	out = pageContext.pushBody();

	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
	encoder.encode(image);
%>
