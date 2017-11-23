package org.lighthouse.crawler.services

import net.ruippeixotog.scalascraper.model.Document
import org.lighthouse.api.Declaration

/**
  * @author nivanov 
  *         on 23.10.2017.
  */
trait DeclarationService {
  def saveDeclaration(declaration: Declaration, page: Document): Unit
}
