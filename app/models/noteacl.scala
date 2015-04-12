package models;

object NotePermission extends Enumeration {
	type NotePermission = Value
	val ReadOnly, Edit, Author = Value
}

import NotePermission._

case class NoteACL(ACLUser:User,Permission:NotePermission)

