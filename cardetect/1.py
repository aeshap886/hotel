import cv2
import numpy as np

# Set video path and create VideoCapture object
video_path = 'video.mp4'
cap = cv2.VideoCapture(video_path)

# Parameters
min_width_rect = 80  # min width rectangle
min_height_rect = 80  # min height rectangle
count_line_position = 550
offset = 6  # allowable error between pixels

# Initialize Subtractor
algo = cv2.bgsegm.createBackgroundSubtractorMOG()

def center_handle(x, y, w, h):
    x1 = int(w / 2)
    y1 = int(h / 2)
    cx = x + x1
    cy = y + y1
    return cx, cy

def detect_vehicles(frame1):
    grey = cv2.cvtColor(frame1, cv2.COLOR_BGR2GRAY)
    blur = cv2.GaussianBlur(grey, (3, 3), 5)
    img_sub = algo.apply(blur)
    dilat = cv2.dilate(img_sub, np.ones((5, 5)), iterations=2)
    kernal = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (5, 5))
    dilat_add = cv2.morphologyEx(dilat, cv2.MORPH_CLOSE, kernal)
    dilat_add = cv2.morphologyEx(dilat_add, cv2.MORPH_CLOSE, kernal)

    counter, _ = cv2.findContours(dilat_add, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    cv2.line(frame1, (25, count_line_position), (1200, count_line_position), (255, 127, 0), 3)

    for (i, c) in enumerate(counter):
        (x, y, w, h) = cv2.boundingRect(c)
        validate_counter = (w >= min_width_rect) and (h >= min_height_rect)
        if not validate_counter:
            continue
        cv2.rectangle(frame1, (x, y), (x + w, y + h), (0, 255, 0), 2)

    detect = [center_handle(x, y, w, h) for c in counter if validate_counter]
    cv2.circle(frame1, (int(np.mean([x for x, y in detect])), count_line_position), 4, (0, 0, 255), -1)

    return frame1, detect

while True:
    ret, frame1 = cap.read()

    if not ret:
        break

    frame1, detect = detect_vehicles(frame1)
    count = sum(1 for y, _ in detect if y < (count_line_position + offset) and y > (count_line_position - offset))
    cv2.putText(frame1, f"Vehicle Counter: {count}", (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 2, (0, 0, 255), 3)
    cv2.imshow('Video Original', frame1)

    if cv2.waitKey(1) == 13:
        break

cv2.destroyAllWindows()
cap.release()