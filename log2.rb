def avg(list)
  list.reduce(:+) / list.size.to_f
end

def get_max(dir)
  files = Dir.glob(dir + "*.csv")
  files.max
end


def parse_nurse(file)
  file = File.open(file,"r")
  first = true
  entries = []
  file.each do |line|
    if first
      first = false
      next
    end
    line = line.split(',')
    timestamp  = line[0].chomp.strip
    name       = line[1].chomp.strip
    vital      = line[2].chomp.strip
    time       = line[3] ? line[3].chomp.strip : nil
    hash = {
      :timestamp => timestamp,
      :name      => name,
      :vital     => vital,
      :time      => time
    }
    entries << hash
  end
  entries
end

def sensor_response_time(bedside)
  avg(bedside.find_all {|i| i[:name] == "Poll Sensor Response Time"}.collect {|i| i[:time]})
end

def vital_sign_push_time(bedside)
  avg(bedside.find_all {|i| i[:name] == "Vital sign Push Time"}.collect {|i| i[:time]})
end

def vital_sign_processing_time(bedside)
  avg(bedside.find_all {|i| i[:name] == "Vital Sign Processing Time"}.collect {|i| i[:time]})
end


def ackAlarms(nurse)
  nurse.find_all {|i| i[:name] == "acknowledgeAlarms"}[0][:time]
end

def ackAlarmsPT(nurse)
  nurse.find_all {|i| i[:name] == "Acknowledge Alarm Processing Time"}[0][:time]
end

def rart(nurse)
  nurse.find_all {|i| i[:name] == "Reset Alarm Response Time"}[0][:time]
end

nurse_file = "/Users/chris/Desktop/Test%204%20Results%20-%20Nurse.csv"
nurse = parse_nurse(nurse_file)

puts "Acknowledge Alarms: " + ackAlarms(nurse)
puts "Acknowdlege Alarm Processing Time: " + ackAlarmsPT(nurse)
puts "Reset Alarm Response Time:" + rart(nurse)
