from sqlalchemy.schema import Column
from sqlalchemy.types import String, Integer, Text
from database import Base


class User(Base):
    __tablename__ = "user"

    user_id = Column(Integer, primary_key=True, index=True)
    user_name = Column(String(50), unique=True)
    user_email = Column(Text())
    user_department = Column(String(20))
    user_password = Column(String(20))
    user_password_repeat = Column(String(20))
    
class Feedback(Base):
    __tablename__ = "user_feedback"

    post_id = Column(Integer, primary_key=True, index=True)
    user_id = Column(String(50))
    post_content = Column(String(500))
    post_date = Column(String(50))    
    