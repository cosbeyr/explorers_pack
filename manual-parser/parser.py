'''
Robin Cosbey -- explorer's pack

'''
import re
import os
import json
import string
from collections import OrderedDict

def rudimentarySearch(data, keyword):

    #removepunct = lambda x: x.translate(str.maketrans('', '', string.punctuation))
    removepunct = lambda x: x

    keyword = keyword.lower()
    #keyword = ' ' + keyword if keyword[0] != ' ' else keyword
    #keyword = keyword + ' ' if keyword[-1] != ' ' else keyword

    output = OrderedDict()
    for chapter in data.keys():
        for section in data[chapter].keys():
            for line in data[chapter][section]:
                #if keyword in line.lower().translate(str.maketrans('', '', string.punctuation)):
                #    output.append('{}; {}\n{}'.format(chapter, section, line))
                line = line.split('.')
                for sentence in line:
                    search = removepunct(sentence.lower())
                    if keyword in search:
                        if chapter in output.keys():
                            if type(output[chapter]) == dict and section in output[chapter].keys():
                                output[chapter][section].append(sentence)
                            else:
                                output[chapter][section] = [sentence]
                        else:
                            output[chapter] = OrderedDict([(section,[sentence])])
    return output

def printSearch(output):
    for key in output.keys():
        print(key)
        for section in output[key].keys():
            for sentence in output[key][section]:
                print('\t{}: {}'.format(section, sentence))
        print()


def getChapters():
    '''
    Get chapter file names and extract titles
    :return a list of the chapter file names and a list of the chapter titles
    '''
    with open('chapters.txt', 'r') as f:
        chapters = f.readlines()

    chapters = [c.strip() for c in chapters]

    addspace = lambda x:  re.sub(r"(\w)([A-Z])", r"\1 \2", x)
    titles = [addspace(c[:-3]) for c in chapters]

    return chapters, titles

def getIntroduction(data):
    '''
    Get the introduction from the current chapter
    :param data (list) the lines from the chapter file
    :return     a list containing the introduction lines and the start of the rest of the file
    '''
    start = 0
    introduction = ''

    for line in data:
        if line[0:4] == '### ':
            break
        introduction += line
        start += 1

    return introduction, start

def getSections(title, data, start):
    '''
    Get the sections (denoted by ###) from the current chapter
    :param title    (str) the title of the chapter
    :param data     (list) the lines from the chapter file
    :param start    (int) the start line after the introduction
    :return         a dictionary containing the sections for the chapter
    '''
    sections = OrderedDict()
 
    for line in data[start:]:
        if line[0:3] == '###':
            curr_section = line[4:].title()
            sections[curr_section] = ''
        else:
            sections[curr_section] += line

    return sections

def getGuideJSON(chapters, titles):
    '''
    Parse through each chapter file and concatenate introduction/section dictionaries
    :param chapters (list) chapter file names
    :param titles   (list) chapter titles
    :return a dictionary containing a dictionary for each chapter 
    '''
    survival_guide = OrderedDict()

    for chapter,title in zip(chapters, titles):
        sections = OrderedDict()
        with open(os.path.join('old/', chapter), 'r') as f:
            data = f.readlines()

        introduction, start = getIntroduction(data)

        if start != 0: 
            sections['Introduction'] = introduction
        if len(data) != start: 
            sections.update(getSections(title, data, start)) 

        survival_guide[title] = sections

    return survival_guide

def main():
    chapters, titles = getChapters()
    survival_guide = getGuideJSON(chapters, titles)

    with open('guide.json', 'w') as f:
        json.dump(survival_guide, f)

    with open('guide.json', 'r') as f:
        data = json.load(f, object_pairs_hook=OrderedDict)

    return data

    # NOTE: differnt ##, ###

if __name__ == '__main__':
    main()
