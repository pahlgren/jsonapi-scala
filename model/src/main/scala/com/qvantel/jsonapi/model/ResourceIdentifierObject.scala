/*
Copyright (c) 2017, Qvantel
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Qvantel nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Qvantel BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.qvantel.jsonapi.model

import _root_.spray.json.DefaultJsonProtocol._
import _root_.spray.json._

final case class ResourceIdentifierObject(id: String, `type`: String, meta: MetaObject)

object ResourceIdentifierObject {
  implicit object ResourceIdentifierObjectJsonFormat extends RootJsonFormat[ResourceIdentifierObject] {
    override def write(obj: ResourceIdentifierObject): JsValue = obj match {
      case ResourceIdentifierObject(id, t, meta) if meta.isEmpty => JsObject("id" -> id.toJson, "type" -> t.toJson)
      case ResourceIdentifierObject(id, t, meta) =>
        JsObject("id" -> id.toJson, "type" -> t.toJson, "meta" -> meta.toJson)
    }

    override def read(json: JsValue): ResourceIdentifierObject = {
      val fields = json.asJsObject.fields
      ResourceIdentifierObject(
        id = fields
          .get("id")
          .map(_.convertTo[String])
          .getOrElse(deserializationError(s"Expected an ‘id’ field in resource identifier object")),
        `type` = fields
          .get("type")
          .map(_.convertTo[String])
          .getOrElse(deserializationError(s"Expected an ‘type’ field in resource identifier object")),
        meta = fields.get("meta").map(_.convertTo[MetaObject]).getOrElse(Map.empty)
      )
    }
  }
}
