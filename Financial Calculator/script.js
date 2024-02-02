function showStartButton() {
    const selectedType = document.getElementById('calculationType').value;
    const startBtn = document.getElementById('startBtn');
    if (selectedType !== 'default') {
      startBtn.classList.remove('hidden');
    } else {
      startBtn.classList.add('hidden');
    }
  }
  
  function calculate() {
    const calculationType = document.getElementById('calculationType').value;
    
    if (calculationType === 'investment') {
      const amount = parseFloat(document.getElementById('investmentAmount').value);
      const interestRate = parseFloat(document.getElementById('interestRate').value);
      const years = parseFloat(document.getElementById('investmentYears').value);
      const interestType = document.getElementById('interestType').value;
      
      let result = 0;
      if (interestType === 'simple') {
        result = amount * (1 + years * interestRate / 100);
      } else if (interestType === 'compound') {
        result = amount * Math.pow((1 + interestRate / 100), years);
      }
      
      document.getElementById('investmentResult').innerText = `Your investment will grow to £${result.toFixed(2)}`;
    } else if (calculationType === 'bond') {
      const presentValue = parseFloat(document.getElementById('houseValue').value);
      const bondInterest = parseFloat(document.getElementById('bondInterest').value);
      const months = parseInt(document.getElementById('repaymentMonths').value);
      
      const i = bondInterest / 12 / 100;
      const repayment = (i * presentValue) / (1 - Math.pow((1 + i), -months));
      
      document.getElementById('bondResult').innerText = `Your repayment each month is £${repayment.toFixed(2)}`;
    }
    
    showResultSection(calculationType);
  }
  
  function showResultSection(calculationType) {
    document.getElementById('investmentInputs').classList.add('hidden');
    document.getElementById('bondInputs').classList.add('hidden');
    
    if (calculationType === 'investment') {
      document.getElementById('investmentInputs').classList.remove('hidden');
    } else if (calculationType === 'bond') {
      document.getElementById('bondInputs').classList.remove('hidden');
    }
  }
  