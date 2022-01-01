import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport.HeaderLicense
import de.heikoseeberger.sbtheader.License
import sbt.url

object LicenseDefinition {
  val template: Option[License.Custom] = Some(HeaderLicense.Custom(
    """scala-biginteger - highly optimized BigInteger implementation for scala, scala-js and scala-native.
      |
      |Copyright 2020, 2021 Kirill A. Korinsky <kirill@korins.ky>
      |Copyright 2022 Kcrypt Lab UG <opensource@kcry.pt>
      |
      |Licensed under the Apache License, Version 2.0 (the "License"); you may not
      |use this file except in compliance with the License. You may obtain a copy of
      |the License at
      |
      |http://www.apache.org/licenses/LICENSE-2.0
      |
      |Unless required by applicable law or agreed to in writing, software
      |distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
      |WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
      |License for the specific language governing permissions and limitations under
      |the License.
      |""".stripMargin
  ))

  val licenses = Seq(
    "Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
  )
}