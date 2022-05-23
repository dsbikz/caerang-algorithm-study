num = int(input())

cnt = 0
for n in range(num):
    word = input()
    wc = 0
    for k in word:
        c = 0
        nc = 1
        for i in word:
            if k == i:
                c += 1
        for j in range(len(word)):
            if word[j] == k:
                idx = j
                break
        while(True):
            if idx == len(word) -1:
                break
            if word[idx] == word[idx+1]:
                nc += 1
                idx +=1
            else:
                break
        if c == nc:
            wc += 1
    if wc == len(word):
        cnt += 1
print(cnt)
