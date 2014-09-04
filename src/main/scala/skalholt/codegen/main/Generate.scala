package skalholt.codegen.main

import com.typesafe.scalalogging.slf4j.LazyLogging
import skalholt.codegen.constants.GenConstants._

object Generate extends LazyLogging {

  def all(arg: GenParam = null) = {
    logger.info("------------ start import and generate ------------")

    val (args, folder) = getArgs(arg)
    constantsLogs(args)

    ImportDesign.main(args)
    GenerateForm.main(args)
    GenerateDao.main(args)
    GenerateController.main(args)
    GenerateRoutes.main(folder)
    GenerateMenu.main(folder)
    GenerateView.main(args)

    logger.info("------------ end import and generate   ------------")
  }

  def importData(arg: GenParam = null) = {
    logger.info("------------ start import ------------")

    val (args, folder) = getArgs(arg)
    constantsLogs(args)

    ImportDesign.main(args)

    logger.info("------------ end import   ------------")
  }

  def generate(arg: GenParam = null) = {
    logger.info("------------ start generate ------------")

    val (args, folder) = getArgs(arg)
    constantsLogs(args)

    GenerateForm.main(args)
    GenerateDao.main(args)
    GenerateController.main(args)
    GenerateRoutes.main(folder)
    GenerateMenu.main(folder)
    GenerateView.main(args)

    logger.info("------------ end generate   ------------")
  }
}