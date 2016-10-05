@echo off
taskkill /f /im "chrome.exe"
taskkill /f /im "chromedriver.exe"
endlocal
exit
rem start /MAX chrome.exe -incognito