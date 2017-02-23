package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileEncryption {
	
	private static final int FLAG = 0x2;

	public static void main(String[] args) throws Exception {
		if(args == null || args.length == 0) {
			System.out.println("empty args, exit");
			return;
		}
		File fileToEncryption = new File(args[0]);
		if(fileToEncryption.isFile() && fileToEncryption.exists()) {
			File newFile = new File(fileToEncryption.getParent(), fileToEncryption.getName() + ".txt");
			if(newFile.exists() && !newFile.delete()) {
				System.out.println("cannot delete, exit(0)");
				System.exit(0);
			} else {
				newFile.createNewFile();
				FileInputStream fis = new FileInputStream(fileToEncryption);
				FileOutputStream fos = new FileOutputStream(newFile);
				FileChannel in = fis.getChannel();
				ByteBuffer bbIn = ByteBuffer.allocate(1024);
				byte[] bytes = new byte[1024];
				int len = -1;
				int index = 0;
				for (;(len = in.read(bbIn)) != -1; bbIn.flip()) {
					bbIn.flip();
					index = 0;
					while(bbIn.hasRemaining()) {
						bytes[index++] = (byte) (bbIn.get() ^ FLAG);
					}
					fos.write(bytes, 0, len);
				}
				fos.close();
				fis.close();
			}
		}
	}

}
