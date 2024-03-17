 $pathToChrome = (Get-Item (Get-ItemProperty 'HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\chrome.exe').'(Default)').VersionInfo.FileName
 $tempFolder = '--user-data-dir=c:\temp'
 $startPage = $PSScriptRoot
 Start-Process -FilePath $pathToChrome -ArgumentList $tempFolder, '--allow-file-access-from-files', $startPage'\index.html'