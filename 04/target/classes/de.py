from botocore.session import Session
from botocore.config import Config

s = Session()
c = s.create_client('s3', 
config=Config(connect_timeout=5, read_timeout=60, retries={'max_attempts': 2}))