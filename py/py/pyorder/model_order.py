from exts_order import db

class EntityBase(object):
    def to_json(self):
        fields = self.__dict__
        if "_sa_instance_state" in fields:
            del fields["_sa_instance_state"]
        return fields

class Order(db.Model, EntityBase):
    # 数据表明、字段
    __tablename__ = 'tb_order'

    id = db.Column(db.Integer, primary_key=True)
    price = db.Column(db.Integer, unique=True)
    name = db.Column(db.String(80), unique=True)
    num = db.Column(db.Integer)
    user_Id = db.Column(db.Integer)