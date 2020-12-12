package com.mysoft.alpha.util;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 *
 */
public class QRCodeUtil {
	//二维码文件名
    public static final String QRCODE_FILENAME = "qrcode.png";
    //字符集
    private static final String CHARSET = "utf-8";
    //二维码格式
    private static final String FORMAT = "PNG";    
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 100;
    // LOGO高度
    private static final int LOGO_HEIGHT = 100;

    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source
     *            二维码图片
     * @param logoPath
     *            LOGO图片地址
     * @param needCompress
     *            是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            throw new Exception("logo file not found.");
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 二维码文件名随机，文件名可能会有重复
     *
     * @param content
     *            内容
     * @param logoPath
     *            LOGO地址
     * @param destPath
     *            存放目录
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static String encode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 调用者指定二维码文件名
     *
     * @param content
     *            内容
     * @param logoPath
     *            LOGO地址
     * @param destPath
     *            存放目录
     * @param fileName
     *            二维码文件名
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        fileName = fileName.substring(0, fileName.indexOf(".")>0?fileName.indexOf("."):fileName.length())
                + "." + FORMAT.toLowerCase();
        if(destPath.endsWith("/")) {
        	destPath = destPath.substring(0,destPath.length()-1);
        }
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．
     * (mkdir如果父目录不存在则会抛出异常)
     * @param destPath
     *            存放目录
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param logoPath
     *            LOGO地址
     * @param destPath
     *            存储地址
     * @throws Exception
     */
    public static String encode(String content, String logoPath, String destPath) throws Exception {
        return QRCodeUtil.encode(content, logoPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param destPath
     *            存储地址
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static String encode(String content, String destPath, boolean needCompress) throws Exception {
        return QRCodeUtil.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param destPath
     *            存储地址
     * @throws Exception
     */
    public static String encode(String content, String destPath) throws Exception {
        return QRCodeUtil.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param logoPath
     *            LOGO地址
     * @param output
     *            输出流
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param output
     *            输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output) throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file
     *            二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path
     *            二维码图片地址
     * @return
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }
    
    /**
     * 直接保存二维码图片
     * @param url 二维码图片url
     * @param destPath 存储路径
     * @param fileName 文件名
     * @return 图片存储地址
     */
    public static String genQRCode(String url,String destPath, String fileName)   throws Exception {
    	CloseableHttpClient httpClient =  HttpClients.createDefault();
    	String genFilePath = "";
        try{
			mkdirs(destPath);
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String contentType = httpResponse.getFirstHeader("content-type").getValue();
				if (contentType.startsWith("image")) {
					if (destPath.endsWith("/")) {
						destPath = destPath.substring(0, destPath.length() - 1);
					}
					genFilePath = destPath + "/" + fileName;
					System.out.println(genFilePath);
					URL input = new URL(url);
					BufferedImage image = ImageIO.read(input);
					ImageIO.write(image, FORMAT, new File(genFilePath));
				} else {
					System.out.println("传入非图片链接:" + url);
				}
				return genFilePath;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
    }

    public static void main(String[] args) throws Exception {
//    	String text = "https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=2_1";
//    	String text = "https://h5.zkydib.com/datamark?bxtfcode=SEFToctEzrw&__pl__=360010001";
        //不含Logo
//        QRCodeUtil.encode(text, null, "e:\\","qrcodeNew400.png", true);
//        QRCodeUtil.encode("https://h5.360huiminbao.cn/datamark?bxtfcode=eSIXwAVFvOI&__pl__=", null, "e:\\","qrcodeOld400.png", true);
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGI8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyMl9aZGNOeUxkS0UxMDAwMHcwN28AAgT6gNFfAwQAAAAA";
        String url2="https://h5.360huiminbao.cn/datamark?bxtfcode=l2TwglEB6xE&__pl__=";
        String pathString = QRCodeUtil.genQRCode(url,  "e:\\qrcode1", "qrcode.png");
        System.out.println("pathString="+pathString);
        //含Logo，不指定二维码图片名
        //QRCodeUtil.encode(text, "e:\\csdn.jpg", "e:\\", true);
        //含Logo，指定二维码图片名
//        QRCodeUtil.encode(text, "d:\\alpha.jpg", "d:\\", "qrcode", true);
//        System.out.println(QRCodeUtil.decode("e:\\qrcode.png"));
    }
}