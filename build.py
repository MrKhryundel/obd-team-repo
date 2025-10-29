import os
os.system("npm run ./src/.vitepress/scripts:build")

for root, dirs, files in os.walk("."):
    if "node_modules" in root or ".git" in root:
        continue
        
    for file in files:
        print(root, file)