import nltk
from nltk.tokenize import word_tokenize
from nltk.stem import PorterStemmer

# Download necessary NLTK resources
nltk.download('punkt')

class HelpDeskExpertSystem:
    def __init__(self):
        """Initialize the expert system with a rule-based knowledge base."""
        self.knowledge_base = {
            "internet": [
                "Check if your router is turned on.",
                "Restart your router and try again.",
                "Reset network settings if the issue persists."
            ],
            "computer_not_starting": [
                "Ensure the power cable is properly connected.",
                "Try using a different power outlet or adapter.",
                "Remove external devices and try again.",
                "Hold the power button for 10 seconds and restart."
            ],
            "slow_computer": [
                "Close unnecessary background applications.",
                "Run a disk cleanup and check for malware.",
                "Upgrade your RAM if the issue persists."
            ],
            "software_crash": [
                "Restart the application.",
                "Check for software updates.",
                "Reinstall the application if the issue continues."
            ],
            "printer_issue": [
                "Ensure the printer is properly connected to the computer.",
                "Check if the correct printer driver is installed.",
                "Try restarting both the printer and computer."
            ]
        }
        self.issue_keywords = {
            "internet": ["internet", "wifi", "network", "connection", "not connecting"],
            "computer_not_starting": [
                "power", "not starting", "won't turn on", "boot", "black screen",
                "no power", "not working", "not booting", "computer dead"
            ],
            "slow_computer": ["slow", "lagging", "freezing", "hang", "performance"],
            "software_crash": ["crash", "not responding", "stopped working", "error", "closing"],
            "printer_issue": ["printer", "not printing", "paper jam", "ink", "not detected"]
        }
        self.stemmer = PorterStemmer()  # For stemming words

    def preprocess_input(self, user_input):
        """Tokenize and stem user input to extract meaningful keywords."""
        tokens = word_tokenize(user_input.lower())
        stemmed_words = [self.stemmer.stem(word) for word in tokens]
        return stemmed_words

    def diagnose_issue(self, user_input):
        """Match user input with the knowledge base."""
        words = self.preprocess_input(user_input)

        for issue, keywords in self.issue_keywords.items():
            if any(keyword in user_input.lower() for keyword in keywords):
                self.provide_solution(issue)
                return
        
        print("\nSorry, we couldn't identify the issue.")
        print("Please describe your problem more specifically or contact IT support.")

    def provide_solution(self, issue):
        """Print suggested solutions for a given issue."""
        solutions = self.knowledge_base.get(issue, [])
        print(f"\nSuggested Solutions for '{issue}':")
        for i, solution in enumerate(solutions, 1):
            print(f"{i}. {solution}")

# Function to run the expert system in a loop
def main():
    expert_system = HelpDeskExpertSystem()

    print(" -----------------** Help Desk Expert System **----------------- ")
    print("Describe your issue (e.g., 'My internet is not working' or 'Laptop is too slow').")
    print("Type 'quit' to exit.")

    while True:
        user_input = input("\nIssue: ").strip().lower()
        
        if user_input == "quit":
            print("Thank you for using the Help Desk Expert System. Goodbye!")
            break
        
        expert_system.diagnose_issue(user_input)

if __name__ == "__main__":
    main()
