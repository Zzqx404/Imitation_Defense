from exts_user import db

class EntityBase(object):
    def to_json(self):
        fields = self.__dict__
        if "_sa_instance_state" in fields:
            del fields["_sa_instance_state"]
        return fields
class User(db.Model, EntityBase):
    # 数据表明、字段
    __tablename__ = 'tb_user'

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True)
    address = db.Column(db.String(120), unique=True)

    # 加一个字段
    Language = 'Python'





