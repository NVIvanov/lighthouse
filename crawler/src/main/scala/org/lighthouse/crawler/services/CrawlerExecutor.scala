package org.lighthouse.crawler.services

/**
  * @author nivanov 
  *         on 02.11.2017.
  */
trait CrawlerExecutor {
  def execute()
  def execute(countries: String*)
  def status: Status

  abstract sealed class Status(value: String) {
    override def toString: String = value
  }

  object Status {
    case object Busy extends Status("busy")
    case object Ready extends Status("ready")
  }
}
