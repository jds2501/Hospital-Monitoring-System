def avg(list)
  list.reduce(:+) / list.size.to_f
end

def get_max(dir)
  files = Dir.glob(dir + "*.csv")
  files.max
end

def parse_bedside(file)
  entries = []
  file = File.open(file,"r")
  file.each do |line|
    line = line.split(',')
    timestamp  = line[0] ? line[0].chomp.strip : nil
    name       = line[1] ? line[1].chomp.strip : nil 
    vital      = line[2] ? line[2].chomp.strip : nil
    time       = line[3] ? line[3].chomp.strip.to_i : nil
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

bedside_dir = "./Implementation/BedsideMonitor/"
bedside_file = get_max(bedside_dir)

#bedside_file= "/Users/chris/Desktop/history_log.csv"

bedside = parse_bedside(bedside_file)



srt = sensor_response_time(bedside)
vspt = vital_sign_push_time(bedside)

puts "Sensor Response Time: " + srt.to_s
puts "Vital Sign Push Time: " + vspt.to_s
puts "Vital Sign Processing Time: " + vital_sign_processing_time(bedside).to_s
puts "Vital Sign Polls per Second: " + ((bedside.count{|i| i[:name] == "pollSensorData"}/srt.to_f)*1000).to_s
puts "Vital Sign Processed per Second: " + ((bedside.count{|i| i[:name] == "pullVitalSign"}/vspt.to_f)*1000).to_s
