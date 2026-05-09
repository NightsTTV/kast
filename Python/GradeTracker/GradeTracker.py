import os

#Check if file exists if not create it
FILENAME = "grade_list.txt" # FILENAME var
grades_list = [] # local list for files
is_on = True
welcome_message = {}

# Startup: Load data
if os.path.exists(FILENAME):
    with open(FILENAME, "r") as f:
        grades_list = [line.strip() for line in f.readlines()]
else:
    with open(FILENAME, "w") as f:
        pass

def calc_grade_avg(): # func to calc grade average
    GRADE_POINTS = {"A": 4, "B": 3, "C": 2, "D": 1, "F": 0} # Default Grades and Numerical Values
    total_points = 0 # starting total of grade points

    if not grades_list:
        print("Error: No grades available to calculate an average.")
        return

    for grade in grades_list:
        points = GRADE_POINTS[grade]
        total_points += points
    total_points = total_points / len(grades_list)
    print(f"{total_points} GPA")


def save_to_file():
    if os.path.exists(FILENAME): 
        with open(FILENAME, "w") as file: # "w" adds a write permission
            for g in grades_list:
                file.write(g + "\n")
        print("...File synced to disk")   
    else:
        print("ERROR: The file does not exist.")
    
def remove():
    choice = input("1. Remove single grade \n2. Remove all grades containing that letter \n:").strip()
    if choice == "1":
        remove_one_instance()
    elif choice == "2":
        remove_grades_x()
    else: 
        print("Sorry wrong value detected!")

def remove_grades_x():
    global grades_list
    letter_to_remove = input("Enter the letter grade you want to remove: ").strip().upper()
 
    if letter_to_remove not in grades_list:
        print(f"No {letter_to_remove} grades were found in the list.")
        return
    
    
    grades_list = [grade for grade in grades_list if grade != letter_to_remove]
    
    print(f"All {letter_to_remove} grades have been removed.")
    save_to_file()

def remove_one_instance():
    letter = input("Which grade would you like to remove one of? ").strip().upper()
    
    if letter in grades_list:
        grades_list.remove(letter)
        print(f"Removed one {letter} from the list.")
        save_to_file()
    else:
        print(f"No {letter} found to remove.")
    
def add_a_grade():  # func to add a grade
    print("A\nB\nC\nD\nF") # list possible user inputs
    grade = input("Enter grade: ").strip().upper() # user input assigned to grade
    
    # Add to the LIST first
    grades_list.append(grade)
    print(f"Grade {grade} added to memory list.") # confirmation statement with grade as output
    save_to_file() # save local info onto disk
    return grade # return grade

def view_grade(): # func to view grade
    if not grades_list:
        print("List is currently empty")
    else: 
        print(f"Current Grades:  {grades_list}")
        print(os.path.getsize(FILENAME))

def welcome(): # function to start the App
    print("\n--- Student Management System ---")
    print("1. Add Grade")
    print("2. View Grades")
    print("3. Calculate Average")
    print("4. Remove Grade")
    print("0. Exit")

navigation = {
    "1": add_a_grade,
    "2": view_grade,
    "3": calc_grade_avg,
    "4": remove,
}

# The Main Execution Loop
print("Welcome!")
name = input("Enter your name: ")

while is_on:
    print(f"\n    User:{name}    ")
    welcome()
    choice = input("Choose an option: ")

    if choice == "0":
        print(f"Goodbye, {name}!")
        is_on = False
    elif choice in navigation:
        # This looks up the function in the dict and runs it
        navigation[choice]() 
    else:
        print("Invalid command.")
