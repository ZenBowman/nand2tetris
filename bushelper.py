
def main():
    lines = []
    while 1:
        line = raw_input("Enter line, empty line to end:\n")
        if line == "":
            break
        else:
            lines.append(line)
    for i in range(16):
        for line in lines:
            print line.replace("0", str(i))

if __name__ == "__main__":
    main()
