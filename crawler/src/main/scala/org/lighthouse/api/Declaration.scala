package org.lighthouse.api

/**
  * @author nivanov 
  *         on 23.10.2017.
  */
trait Declaration {
  def name: String
  def country: String
  def link: String
  def function: String
  def year: Integer
  def `type`: String
}
