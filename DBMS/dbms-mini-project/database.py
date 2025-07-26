from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

SQLALCHEMY_DATABASE_URL = "mysql+mysqlconnector://root:Neeti_0107@localhost:3306/studentfeedback"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL
)
Session = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()