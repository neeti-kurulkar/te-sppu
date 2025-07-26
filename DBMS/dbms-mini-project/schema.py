from pydantic import BaseModel

class User(BaseModel):
    user_id = int
    user_name = str
    user_email = str
    user_department = str
    user_password = str
    user_password_repeat = str
    
    class Config:
        orm_mode = True
        
class Feedback:
    post_id = int
    user_id = int
    post_content = str
    
    class Config:
        orm_mode = True