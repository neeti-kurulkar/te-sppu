import nltk
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import word_tokenize

nltk.download('punkt_tab')
nltk.download('wordnet')

lemmatizer = WordNetLemmatizer()

# Expanded greetings and responses
greetings = ['hello', 'hi', 'hey', 'greetings', 'what’s up', 'good morning', 'good evening']
farewells = ['bye', 'goodbye', 'see you', 'quit', 'exit']

responses = {
    # Common questions
    'hello': 'Hello! How can I assist you today?',
    'how are you': 'I\'m doing great! Thanks for asking. How about you?',
    'what is your name': 'I am ChatBot, your virtual assistant!',
    'what can you do': 'I can answer questions, chat with you, and provide general information.',
    
    # General knowledge questions
    'what is the capital of india': 'The capital of India is New Delhi.',
    'who is the prime minister of india': 'The Prime Minister of India is Narendra Modi.',
    
    # Tech-related questions
    'what is python': 'Python is a high-level, interpreted programming language known for its simplicity.',
    'what is machine learning': 'Machine Learning is a field of AI that enables computers to learn from data and make decisions.',
    'what is ai': 'AI (Artificial Intelligence) is the simulation of human intelligence in machines.',
    
    # Fun & Small talk
    'tell me a joke': 'Why do programmers prefer dark mode? Because light attracts bugs! 😆',
    'do you have feelings': 'I don\'t have emotions, but I can simulate friendly conversations!',
    'can you sing': 'I can\'t sing, but I can recommend great music! What genre do you like?',
    
    # Assistance-related
    'how can you help me': 'I can answer questions, provide general information, and assist with basic tasks.',
    'can you remind me of something': 'I currently don’t have memory, but you can use a reminder app Hfor that!',
}

# Function to process user input
def process_input(user_input):
    user_input = user_input.lower()
    tokens = word_tokenize(user_input)  # Tokenize input
    tokens = [lemmatizer.lemmatize(word) for word in tokens]  # Lemmatize input

    # Check for greetings
    for word in tokens:
        if word in greetings:
            return responses['hello']

    # Check for predefined responses
    for phrase in responses.keys():
        if phrase in user_input:
            return responses[phrase]

    # Check for farewells
    for word in tokens:
        if word in farewells:
            return "Goodbye! Have a great day!"

    return "I'm not sure I understand. Can you rephrase that?"

# Chatbot loop
def chatbot():
    print("Welcome to the chatbot! Type 'quit' to exit.")
    while True:
        user_input = input("You: ")
        if user_input.lower() in farewells:
            print("ChatBot: Goodbye! Have a nice day!")
            break
        print("ChatBot:", process_input(user_input))

# Run the chatbot
chatbot()
