GRADE_POINTS = {"A": 4, "B": 3, "C": 2, "D": 1, "F": 0}
my_grades = ["A", "B", "C", "D", "F"]
num_total = 0

for grade in my_grades: 
    match grade:
        case "A":
            num_total += 4
            print("+ 4 ")
        case "B":
            num_total += 3
            print("+ 3 ")
        case "C":
            num_total += 2
            print("+ 2 ")
        case "D":
            num_total += 1
            print("+ 1 ")
        case "F":
            num_total += 0

print(f"Total calculated from switch case: {num_total}")

for grade in my_grades:
    points = GRADE_POINTS[grade]
num_total = num_total/len(my_grades)
print(f"Total calculated from dictionary mapping: {num_total}")


