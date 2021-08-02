/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.rd.web.helper

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.model.User
import org.openurp.rd.achievement.model.{RdAchievement, RdAchievementMember}

import java.time.Instant

class RdAchievementImportListener(entityDao: EntityDao) extends ImportListener {

  private val teacherUserFetcher = new TeacherUserFetcher(entityDao)

  override def onStart(tr: ImportResult): Unit = {}

  override def onFinish(tr: ImportResult): Unit = {}

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("rdAchievement.code") foreach { code =>
      val cs = entityDao.findBy(classOf[RdAchievement], "code", List(code))
      if (cs.nonEmpty) {
        transfer.current = cs.head
      }
    }
  }


  override def onItemFinish(tr: ImportResult): Unit = {
    val achievement = transfer.current.asInstanceOf[RdAchievement]

    achievement.updatedAt = Instant.now
    transfer.curData.get("memberNames") foreach { memberNames =>
      if (null != memberNames) {
        var idx = 1
        teacherUserFetcher.processNames(memberNames).foreach { name =>
          val rs = teacherUserFetcher.getUserByName(name)
          val member = achievement.getMember(idx) match {
            case Some(m) => m
            case None =>
              val member = new RdAchievementMember()
              member.achievement = achievement
              member.idx = idx
              achievement.members += member
              member
          }
          if (Strings.isEmpty(rs._2)) {
            member.user = Some(rs._1.get)
            member.name = rs._1.get.name
          } else {
            member.name = name
            tr.addFailure("找不到唯一的完成人", rs._2)
          }
          idx += 1
        }
        achievement.members --= achievement.members.find(_.idx >= idx)
      }
    }

    if (achievement.members.nonEmpty && achievement.achievementType != null) {
      entityDao.saveOrUpdate(achievement)
    }
  }
}
