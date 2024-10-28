package restApi

import models.TaskResultOutput
import play.api.Configuration
import play.api.libs.json.{Format, Json}
import play.api.libs.ws.WSClient

import javax.inject.Inject
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

class RestController @Inject()(wsClient: WSClient,
                               config: Configuration,
                              )(implicit ec: ExecutionContext) {
  private val indexerTimeout = 2.second


  def waitForTask(url: String): Future[TaskResultOutput] = {

    wsClient.url(url)
      .withRequestTimeout(indexerTimeout)
      .withHttpHeaders("Content-Type" -> "application/json")
      .get()
      .map { data =>
        data.json.as[TaskResultOutput]
      }
  }
}





