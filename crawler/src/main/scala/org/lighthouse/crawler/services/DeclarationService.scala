package org.lighthouse.crawler.services

import org.lighthouse.api.Declaration

/**
  * @author nivanov 
  *         on 23.10.2017.
  */
trait DeclarationService {
  def saveDeclaration(declaration: Declaration): Unit
}
