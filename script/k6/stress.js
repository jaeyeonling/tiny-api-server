import http from 'k6/http';

export default function() {
  checkNotFound();
  checkHealth();
  checkEngine();
  checkEngineTask();
  checkEngineTaskCommandHistory();
  checkEngineTaskQueryHistory();
}

function checkNotFound() {
  get(randomString(20));
}

function checkHealth() {
  get('health');
}

function checkEngine() {
  // engine save
  const engineId = post('engines', { name: randomString(100) }).id;

  // engine findAll
  get('engines');

  // engine findById
  get(`engines/${engineId}`);

  // engine run
  post(`engines/${engineId}/run`, { description: 'description', complex: 1 });
}

function checkEngineTask() {
  // engineTask findAll
  const engineId = post('engines', { name: randomString(100) }).id;
  get(`engines/${engineId}/tasks`);

  // engineTask findById
  const engineTaskId = post(`engines/${engineId}/run`, { description: 'description' }).id;
  get(`engines/${engineId}/tasks/${engineTaskId}`);

  // engineTask report
  get(`engines/${engineId}/tasks/${engineTaskId}/reports`);
}

function checkEngineTaskCommandHistory() {
  // engineTask commandHistory findAll
  get(`engines/ignore/tasks/ignore/commands`);
}

function checkEngineTaskQueryHistory() {
  // engineTask queryHistory findAll
  get(`engines/ignore/tasks/ignore/queries`);
}

function get(path) {
  return parse(http.get(createURL(path)));
}

function post(path, body) {
  const headers = {
    'Content-Type': 'application/json'
  };
  const params = {
    headers
  };

  return parse(http.post(createURL(path), JSON.stringify(body), params));
}

function createURL(path) {
  const host = "http://localhost:8080";
  return `${host}/${path}`;
}

function parse(res) {
  try {
    return JSON.parse(res.body);
  } catch (e) {
    if (e.message !== 'EOF') {
      log(e);
    }
  }
}

function randomString(len, charSet) {
  charSet = charSet || 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

  let randomString = '';
  for (let i = 0; i < len; i++) {
    const randomPosition = Math.floor(Math.random() * charSet.length);
    randomString += charSet.substring(randomPosition, randomPosition + 1);
  }

  return randomString;
}

function log(obj) {
  console.log(JSON.stringify(obj));
}