from fastapi import FastAPI

app = FastAPI(title="Campus Intelligence Analytics")

@app.get("/health")
def health():
    return {"status": "ok", "service": "analytics"}
