import React, { useRef, useState } from 'react';
// import '../css/styles.css';
// import { useDetectOutsideClick } from '../Hooks/useDetectOutsideClick.js';
import '../../css/styles.css';
import { ImageMapAttributes } from '../../Constants.js';
import ImageMapper from 'react-image-mapper';
import Canvas from './Canvas.jsx';

const CanvasWrapper = () => {
    const { Height, Width, FillColor, StrokeColor } = ImageMapAttributes;

    const canvasRef = useRef(null);
    const imgRef = useRef(null);
    const [currentArea, setCurrentArea] = useState(null);
    // const [isActive, setIsActive] = useDetectOutsideClick(dropdownRef, false);
    // const onClick = () => setIsActive(!isActive);

    const imageUrl = 'http://localhost:3000/api/image/page-000.png';

    const drawContent = (context) => {
        // Insert your canvas API code to draw an image
        const image = new Image();
        image.src = imageUrl;
        context.drawImage(image, 0, 0);
    };

    // [1218, 320, 495, 50],
    // [1220, 443, 585, 48],
    // [347, 578, 1424, 157],
    // [1308, 743, 756, 234],
    // [1307, 728, 660, 4],
    // [1308, 1050, 863, 50],

    const trueImageWidth = 2538;
    const trueImageHeight = 3283;

    // const imageRatio =

    // const renderImageOnCanvas = () => {
    //     canvasRef.current.getContext().drawImage(base_image, 0, 0);
    // };

    return (
        <div>
            <Canvas
                height={trueImageHeight}
                width={trueImageWidth}
                draw={drawContent}
            ></Canvas>
        </div>
    );
};

export default CanvasWrapper;
