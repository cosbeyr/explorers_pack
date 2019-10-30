import json
from objdict import ObjDict

data = ObjDict()
title = ""
section = ""

chapters = open("chapters.txt")
file = chapters.readline().replace("\n","")	

#loop over all chapter files
while file:

	chapterName = file[0:-3]
	inIntro = 1
	f = open("old/" + file)
	line = f.readline().replace("\n","")
	line = line[0:-1]

	chapterData = ObjDict()

	#loop over content in file
	while line:
		line = line.replace("\n","")
		if line[0:3] == "###":
			#new section
			if inIntro == 1:
				inIntro = 0
				chapterData.main = section
			else:
				chapterData[title] = section
			title = line[4:-1]
			section = ""
		else:
			section += line
		line = f.readline()

	#don't forget to save the last section
	if inIntro == 1:
		inIntro = 0
		chapterData.main = section
	else:
		chapterData[title] = section
	title = line[4:-1]
	section = ""


	data[chapterName] = chapterData
	f.close()

	file = chapters.readline().replace("\n","")


chapters.close()

f = open("manual.json", "w")
json.dump(data,f)
f.close()
