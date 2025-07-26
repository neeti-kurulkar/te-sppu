from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

from model import User, Feedback
import schema
from database import Session, engine
import model

def get_database_session():
    try:
        db = Session()
        yield db
    finally:
        db.close()
        
app = FastAPI()
model.Base.metadata.create_all(bind=engine)

app.mount("/static", StaticFiles(directory="static"), name="static")
templates = Jinja2Templates(directory="templates")
@app.get("/", response_class=HTMLResponse)
async def read_item(request: Request, db: Session = Depends(get_database_session)):
    records = db.query(User).all()
    return templates.TemplateResponse("index.html", {"request": request, "data": records})