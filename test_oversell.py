import threading
import requests
import time

# 設定參數
URL = "http://app:8080/orders?userId=1" # 對應你的docker裡面的服務app(後端)
TOTAL_THREADS = 20  # 模擬 20 個人同時搶購
STOCK_TO_BUY = 1    # 每人買 1 個

# 用來同步所有執行緒的「起跑門」
start_trigger = threading.Event()

def place_order(thread_id):
    # 先在起跑線等待
    start_trigger.wait()

    payload = [{"productId": 1, "quantity": STOCK_TO_BUY}] # 對應你的 DTO
    try:
        response = requests.post(URL, json=payload, timeout=5)
        print(f"Thread-{thread_id}: Status {response.status_code}, Body: {response.text[:50]}...")
    except Exception as e:
        print(f"Thread-{thread_id}: Failed - {e}")

# 1. 建立執行緒池
threads = []
for i in range(TOTAL_THREADS):
    t = threading.Thread(target=place_order, args=(i,))
    threads.append(t)
    t.start()

print(f"🚀 已準備好 {TOTAL_THREADS} 個執行緒，準備開搶...")
time.sleep(20) # 給系統一點準備時間

# 2. 鳴槍起跑！所有執行緒會同時衝出 start_trigger.wait()
start_trigger.set()

# 3. 等待所有執行緒結束
for t in threads:
    t.join()

print("\n✅ 測試結束，請去 MySQL 檢查結果。")