package skalholt.codegen.util
import java.io.{ FileOutputStream, IOException, FileNotFoundException, BufferedWriter, OutputStreamWriter, File }
import skalholt.codegen.constants.GenConstants._

object FileUtil {
  def createFile(str: String, filePath: String, fileNm: String, overwrite: Boolean = false) = {

    val file = new File(filePath + "/" + fileNm)
    if (overwrite || !file.exists) {

      createDirectory(filePath)

      val fos = new FileOutputStream(filePath + "/" + fileNm)
      try {
        outputFile(str, fos)
      } catch {
        case e: IOException => e.printStackTrace
      } finally {
        fos.close()
      }
    }
  }

  def outputFile(str: String, fos: FileOutputStream) = {
    val osw = new OutputStreamWriter(fos, "UTF-8")
    val bw = new BufferedWriter(osw)
    try {
      bw.write(str)
    } catch {
      case e: IOException => e.printStackTrace
    } finally {
      try {
        if (bw != null) bw.close

      } catch {
        case e: IOException => e.printStackTrace
      }
      try {
        osw.close
      } catch {
        case e: IOException => e.printStackTrace
      }
    }
  }

  def createDirectory(createPath: String) = {
    val targetDirectory = new File(createPath)
    if (!targetDirectory.exists() && !targetDirectory.mkdirs())
      throw new RuntimeException("フォルダを作成できませんでした。[" + createPath + "]")
  }

}