import os


def print_to_file(directory, file_name, text):
    text_file = open(directory + '/' + file_name, "w")
    text_file.write(text)
    text_file.close()


def create_directory(directory_path):
    if not os.path.exists(directory_path):
        os.makedirs(directory_path)