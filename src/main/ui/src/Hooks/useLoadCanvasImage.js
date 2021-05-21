import { useState, useEffect } from 'react';

export const useLoadCanvasImage = (canvasRef) => {
    const [isActive, setIsActive] = useState(initialState);

    useEffect(() => {
        const canvasContext = canvasRef?.current?.getContext('2d');
        canvasContext.drawImage(img, 0, 0);
    }, []);

    return [isActive, setIsActive];
};
