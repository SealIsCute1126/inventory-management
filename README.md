Inventory Management System (High Concurrency)
這是一個基於 Java Spring Boot 3 開發的庫存管理系統，專門針對電商場景中的「高併發搶購」進行優化。本專案已成功部署於 Google Cloud Platform (GCP)，並通過 Docker 容器化技術實現全自動化部署。

🚀 技術棧 (Tech Stack)
Backend: Java 21, Spring Boot 3.2.3, Spring Data JPA

Database: MySQL 8.0

Infrastructure: Google Cloud Platform (GCP Compute Engine)

DevOps: Docker, Docker Compose

Testing: Python (Multithreading Stress Test)

💡 核心亮點：解決超賣問題 (Concurrency Control)
在電商系統中，多個使用者同時下單導致「庫存變負數」是致命問題。本專案透過以下機制確保資料一致性：

資料庫事務 (Transactional)：確保訂單建立與庫存扣減原子化。

並發處理邏輯：使用 Pessimistic/Optimistic Locking 機制，防止多執行緒同時修改同一筆庫存資料。

🧪 壓力測試結果 (Stress Test)
使用專案內的 test_oversell.py 進行 20 個執行緒同時搶購 10 件商品的測試：

測試結果：10 名使用者成功下單 (Status 200)，其餘 10 名使用者收到「庫存不足」回傳 (Status 400)。

資料庫驗證：最終庫存精確歸零，無任何超賣現象發生。

🛠️ 快速啟動 (Deployment)
專案已完全容器化，只需一行指令即可在任何環境啟動：
sudo docker-compose up -d --build
