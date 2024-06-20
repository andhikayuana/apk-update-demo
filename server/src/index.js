const path = require('path');
const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
    res.json({ 
        app: 'apk update checker',
        time: Date.now()
    })
})

app.get('/v1/app/latest', (req, res) => {
    res.json({
        version_code: 2,
        apk_url: 'https://bb0f-104-28-251-244.ngrok-free.app/v1/app/storage/app-debug.apk'
    })
})
app.use("/v1/app/storage", express.static(path.resolve(__dirname, '../storage')));

app.listen(port, () => {
    console.log(`app listening on port ${port}`)
})